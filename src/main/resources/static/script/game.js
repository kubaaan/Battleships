import { config } from "./utilities/gameConfig.js";
import * as support from "./utilities/supportingFunctions.js";

//EVENT HANDLERS//

$(document).ready(function (e) {
  $.get("deploy", function (res) {
    config.shipsToDeploy = support.sortObjectPropertiesByValue(
      res.shipsToDeploy
    );
    config.shipsToDeployNames = Object.keys(config.shipsToDeploy);
    config.nextShipName = config.shipsToDeployNames.shift();
    config.nextShipLength = config.shipsToDeploy[config.nextShipName];
  });
});

$(".player-one .field").hover(
  support.applyClassForHover,
  support.removeHoverClasses
);

$(".player-one .field").click(function (e) {
  if (config.gameMode === "deploy") {
    if ($(this).hasClass("hover") && !$(this).hasClass("deployed")) {
      let hoveredIndex = $(this).index();
      let incrementFactor = config.shipDirection === "RIGHT" ? 10 : 1;

      for (var i = 0; i < config.nextShipLength; i++) {
        let index = hoveredIndex + i * incrementFactor + 1;

        $(`#P1_${index}`).addClass("deployed");
      }

      const request = {
        shipType: config.nextShipName,
        address: hoveredIndex,
        direction: config.shipDirection,
        length: config.nextShipLength,
      };

      $.ajax({
        url: "deploy",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(request),
      });

      if (config.shipsToDeployNames.length === 0) {
        config.nextShipLength = 0;
      }

      if (config.shipsToDeployNames.length > 0) {
        config.nextShipName = config.shipsToDeployNames.shift();
        config.nextShipLength = config.shipsToDeploy[config.nextShipName];
      }
    }
  }

  if (config.shipsToDeployNames.length === 0 && config.nextShipLength === 0) {
    config.gameMode = "guess";
    $(".player-one .field").off("click");
    $(".player-one .field").off("mouseenter mouseleave");
    $(".game-header").text("Guess the location of Player Two's ships");
    setPlayerTwoEventListeners();
  }
});

$(".player-one .field").contextmenu(support.rightClick);

function setPlayerTwoEventListeners() {
  $(".player-two .field").hover(
    (e) => $(e.currentTarget).addClass("hover"),
    (e) => $(e.currentTarget).removeClass("hover")
  );

  $(".player-two .field").click(function (e) {
    if (!$(this).hasClass("revealed")) {
      const targetAddress = e.target.id.substring(3);

      $.get(`guess?address=${targetAddress}`, function (res) {
        if (res.gameEnded == true) {
          $.get("endgame", function (res) {
            $(".game-header").text(res);
            $(".player-two .field").off("click");
            $(".player-two .field").off("mouseenter mouseleave");
            return;
          });
        }

        if (res.guessResult === "OCCUPIED") {
          $(`#P2_${targetAddress}`).addClass("deployed");
        }
        $(`#P2_${targetAddress}`).addClass("revealed");

        $(`#P1_${res.guess}`).addClass("revealed");
      });
    }
  });
}
