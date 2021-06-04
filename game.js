let shipDirection = "vertical";
let gameMode = "deploy";
let shipLength = 3;

//EVENT HANDLERS//

$(".player-one .field").click(function (e) {
  if (gameMode === "deploy") {
    if ($(this).hasClass("hover")) {
      hoveredIndex = $(this).index();
      let incrementFactor = shipDirection === "vertical" ? 10 : 1;

      for (var i = 0; i < shipLength; i++) {
        let index = hoveredIndex + i * incrementFactor + 1;

        $(".player-one .field:nth-child(" + index + ")").addClass("deployed");
      }
    }
  }
});

$(".player-one .field").contextmenu(function (e) {
  e.preventDefault();
  shipDirection = shipDirection === "vertical" ? "horizontal" : "vertical";
  removeHoverClasses();
  addHoverClass($(this).index());
});

$(".player-one .field").hover(applyClassForHover, removeHoverClasses);

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
  let endIndex = hoveredIndex + (shipLength - 1) * incrementFactor;

  if (shipDirection === "vertical" && endIndex >= 100) {
    appliedClass = "invalid";
  }

  if (
    shipDirection === "horizontal" &&
    Math.floor(hoveredIndex / 10) != Math.floor(endIndex / 10)
  ) {
    appliedClass = "invalid";
  }

  for (var i = 0; i < shipLength; i++) {
    let index = hoveredIndex + i * incrementFactor + 1;

    if ($(".player-one .field:nth-child(" + index + ")").hasClass("deployed")) {
      appliedClass = "invalid";
      break;
    }
  }

  for (var i = 0; i < shipLength; i++) {
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
