let shipDirection = "DOWN";
let gameMode = "deploy";
let nextShipName = "";
let nextShipLength = 0;
let shipsToDeploy;
let shipsToDeployNames;

//EVENT HANDLERS//

$(document).ready(function (e) {
  $.get("deploy", function (res) {
    shipsToDeploy = res.shipsToDeploy;
    shipsToDeployNames = Object.keys(shipsToDeploy);
    nextShipName = shipsToDeployNames.shift();
    nextShipLength = shipsToDeploy[nextShipName];
  });
});

$(".player-one .field").hover(applyClassForHover, removeHoverClasses);

$(".player-one .field").click(function (e) {
  if (gameMode === "deploy") {
    if ($(this).hasClass("hover") && !$(this).hasClass("deployed")) {
      hoveredIndex = $(this).index();
      let incrementFactor = shipDirection === "RIGHT" ? 10 : 1;

      for (var i = 0; i < nextShipLength; i++) {
        let index = hoveredIndex + i * incrementFactor + 1;

        $(".player-one .field:nth-child(" + index + ")").addClass("deployed");
      }

      const request = {
        shipType: nextShipName,
        address: hoveredIndex,
        direction: shipDirection,
        length: nextShipLength,
      };

      $.ajax({
        url: "deploy",
        type: "POST",
        dataType: "xml/html/script/json", // expected format for response
        contentType: "application/json",
        data: JSON.stringify(request),
      });

      if (shipsToDeployNames.length === 0) {
        nextShipLength = 0;
      }

      if (shipsToDeployNames.length > 0) {
        nextShipName = shipsToDeployNames.shift();
        nextShipLength = shipsToDeploy[nextShipName];
      }
    }
  }

  if ((shipsToDeployNames.length === 0) & (nextShipLength === 0)) {
    gameMode = "guess";
    $(".player-one .field").off("click");
    $(".player-one .field").off("hover");
    $(".game-header").text("Guess the location of Player Two's ships");
    setPlayerTwoEventListeners();
  }
});

$(".player-one .field").contextmenu(function (e) {
  e.preventDefault();
  shipDirection = shipDirection === "DOWN" ? "RIGHT" : "DOWN";
  removeHoverClasses();
  addHoverClass($(this).index());
});

function setPlayerTwoEventListeners() {
  $(".player-two .field").hover(
    function (e) {
      $(this).addClass("hover");
    },
    function (e) {
      $(this).removeClass("hover");
    }
  );

  $(".player-two .field").click(function (e) {
    if (!$(this).hasClass("revealed")) {
      const targetAddress = e.target.id.substring(3);

      $.get("guess?address=" + targetAddress, function (res) {
        if (res.guessResult === "OCCUPIED") {
          $("#P2_" + targetAddress).addClass("deployed");
        }
        $("#P2_" + targetAddress).addClass("revealed");

        $("#P1_" + res.guess).addClass("revealed");
      });
    }
  });
}

//SUPPORTING FUNCTIONS//

function applyClassForHover(e) {
  addHoverClass($(this).index());
}

function removeHoverClasses() {
  $(".player-one").children().removeClass("hover");
  $(".player-one").children().removeClass("invalid");
}

function addHoverClass(hoveredIndex) {
  let incrementFactor = shipDirection === "RIGHT" ? 10 : 1;
  let appliedClass = "hover";
  let endIndex = hoveredIndex + (nextShipLength - 1) * incrementFactor;

  if (shipDirection === "RIGHT" && endIndex >= 100) {
    appliedClass = "invalid";
  }

  if (
    shipDirection === "DOWN" &&
    Math.floor(hoveredIndex / 10) != Math.floor(endIndex / 10)
  ) {
    appliedClass = "invalid";
  }

  for (var i = 0; i < nextShipLength; i++) {
    let index = hoveredIndex + i * incrementFactor + 1;

    if ($(".player-one .field:nth-child(" + index + ")").hasClass("deployed")) {
      appliedClass = "invalid";
      break;
    }
  }

  for (var i = 0; i < nextShipLength; i++) {
    let index = hoveredIndex + i * incrementFactor + 1;

    if (
      shipDirection === "DOWN" &&
      Math.floor(hoveredIndex / 10) != Math.floor((index - 1) / 10)
    ) {
      break;
    }

    $(".player-one .field:nth-child(" + index + ")").addClass(appliedClass);
  }
}
