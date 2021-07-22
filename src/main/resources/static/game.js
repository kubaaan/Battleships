let shipDirection = "vertical";
let gameMode = "deploy";
let nextShipLength = 0;
let shipsToDeploy = [];

//EVENT HANDLERS//

$(document).ready(function (e) {
  $.get("deploy", function (res) {
    nextShipLength = res.shipsToDeploy.shift();
    shipsToDeploy = res.shipsToDeploy;
  });
});

$(".player-one .field").hover(applyClassForHover, removeHoverClasses);

$(".player-one .field").click(function (e) {
  if (gameMode === "deploy") {
    if ($(this).hasClass("hover") && !$(this).hasClass("deployed")) {
      hoveredIndex = $(this).index();
      let incrementFactor = shipDirection === "vertical" ? 10 : 1;

      for (var i = 0; i < nextShipLength; i++) {
        let index = hoveredIndex + i * incrementFactor + 1;

        $(".player-one .field:nth-child(" + index + ")").addClass("deployed");
      }

      if (shipsToDeploy.length === 0) {
        nextShipLength = 0;
      }

      if (shipsToDeploy.length > 0) {
        nextShipLength = shipsToDeploy.shift();
      }

      const request = {
        address: hoveredIndex,
        direction: shipDirection,
      };

      $.ajax({
        url: "deploy",
        type: "POST",
        dataType: "xml/html/script/json", // expected format for response
        contentType: "application/json",
        data: JSON.stringify(request),
      });
    }
  }

  if (shipsToDeploy.length === 0 & nextShipLength === 0) {
    gameMode = "guess";
    $(".player-one .field").off("click");
    $(".player-one .field").off("hover");
  }
});

$(".player-one .field").contextmenu(function (e) {
  e.preventDefault();
  shipDirection = shipDirection === "vertical" ? "horizontal" : "vertical";
  removeHoverClasses();
  addHoverClass($(this).index());
});


$(".player-two .field").hover(function(e){
  $(this).addClass("hover");
}, function(e){
  $(this).removeClass("hover");
});

$(".player-two .field").click(function(e){

  const targetAddress = e.target.id.substring(3);
  
  $.get("guess?address=" + targetAddress, function(res){

    if(res.guessResult === "HIT"){
      $("#P2_" + targetAddress).addClass("deployed");
    }
    $("#P2_" + targetAddress).addClass("revealed");

    $("#P1_" + res.guess).addClass("revealed");
    const isGuessDeployed = $("#P1_" + res.guess).hasClass("deployed");

    const request = {
      guess: res.guess,
      guessResult: isGuessDeployed? "HIT" : "EMPTY"
    }

    $.ajax({
      url: "guess",
      type: "POST",
      dataType: "xml/html/script/json", // expected format for response
      contentType: "application/json",
      data: JSON.stringify(request),
    });

  })


});

//SUPPORTING FUNCTIONS//

function applyClassForHover(e) {
  addHoverClass($(this).index());
}

function removeHoverClasses() {
  $(".player-one").children().removeClass("hover");
  $(".player-one").children().removeClass("invalid");
}

function addHoverClass(hoveredIndex) {
  let incrementFactor = shipDirection === "vertical" ? 10 : 1;
  let appliedClass = "hover";
  let endIndex = hoveredIndex + (nextShipLength - 1) * incrementFactor;

  if (shipDirection === "vertical" && endIndex >= 100) {
    appliedClass = "invalid";
  }

  if (
    shipDirection === "horizontal" &&
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
      shipDirection === "horizontal" &&
      Math.floor(hoveredIndex / 10) != Math.floor((index - 1) / 10)
    ) {
      break;
    }

    $(".player-one .field:nth-child(" + index + ")").addClass(appliedClass);
  }
}
