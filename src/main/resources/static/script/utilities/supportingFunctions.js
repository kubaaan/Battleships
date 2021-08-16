import { config } from "./gameConfig.js";

export function applyClassForHover(e) {
  addHoverClass($(this).index());
}

export function removeHoverClasses() {
  $(".player-one").children().removeClass("hover");
  $(".player-one").children().removeClass("invalid");
}

export function rightClick(e) {
  e.preventDefault();
  config.shipDirection = config.shipDirection === "DOWN" ? "RIGHT" : "DOWN";
  removeHoverClasses();
  addHoverClass($(this).index());
}

export function addHoverClass(hoveredIndex) {
  let incrementFactor = config.shipDirection === "RIGHT" ? 10 : 1;
  const appliedClass = determineHoverClass(hoveredIndex, incrementFactor);

  for (var i = 0; i < config.nextShipLength; i++) {
    let index = hoveredIndex + i * incrementFactor;

    if (!areFieldsInLine(hoveredIndex, index)) {
      break;
    }

    $(`#P1_${index}`).addClass(appliedClass);
  }
}

export function sortObjectPropertiesByValue(object) {
  const sortable = Object.entries(object)
    .sort(([, a], [, b]) => b - a)
    .reduce((r, [k, v]) => ({ ...r, [k]: v }), {});
  return sortable;
}

function determineHoverClass(hoveredIndex, incrementFactor) {
  let appliedClass = "hover";
  let endIndex = hoveredIndex + (config.nextShipLength - 1) * incrementFactor;

  if (!areFieldsInLine(hoveredIndex, endIndex)) {
    appliedClass = "invalid";
  }

  for (var i = 0; i < config.nextShipLength; i++) {
    let index = hoveredIndex + i * incrementFactor;

    if ($(`#P1_${index}`).hasClass("deployed")) {
      appliedClass = "invalid";
      break;
    }
  }

  return appliedClass;
}

function areFieldsInLine(fieldOneIndex, fieldTwoIndex, boardSize = 10) {
  if (fieldTwoIndex >= boardSize ** 2) return false;
  if (fieldOneIndex % boardSize === fieldTwoIndex % boardSize) return true;
  if (
    Math.floor(fieldOneIndex / boardSize) ===
    Math.floor(fieldTwoIndex / boardSize)
  )
    return true;
  return false;
}
