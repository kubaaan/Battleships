$(document).ready(function (e) {
  $.get("simulate", function (res) {
    const simulationResult = simulationResultAdapter(res);
    displaySimulationResult(simulationResult);
  });
});

function displaySimulationResult(simulationResult) {
  const timeout = 1000;
  simulationResult.forEach((guess, index) => {
    let i = ++index;
    setTimeout(() => {
      markResultOnBoard(guess);
    }, i * timeout);
  });
}

function markResultOnBoard(guess) {
  const index = guess.guess;
  $(index).addClass("revealed");
  if (guess.guessResult === "OCCUPIED") $(index).addClass("deployed");
  if (guess.gameEnded === true) $(".game-header").text("Game ended");
}

// function simulationResultAdapter(simulationResult) {
//   const convertedResult = [];

//   simulationResult.forEach((apiGuess, index) => {
//     const guessAddress =
//       index % 2 === 0 ? `#P2_${apiGuess.guess}` : `#P1_${apiGuess.guess}`;

//     const newGuess = {
//       guess: guessAddress,
//       guessResult: apiGuess.guessResult,
//       gameEnded: apiGuess.gameEnded,
//     };

//     convertedResult.push(newGuess);
//   });

//   return convertedResult;
// }

function simulationResultAdapter(simulationResult) {
  const convertedResult = simulationResult.reduce((total, element) => {
    let guess = convertApiGuess(element.guessRequest, 0);
    total.push(guess);
    guess = convertApiGuess(element.guessResponse, 1);
    total.push(guess);
    return total;
  }, []);

  //// napisać funkcję która przerabia adresy strzałów na `#P2_${apiGuess.guess}` : `#P1_${apiGuess.guess}`;
  return convertedResult;
}

function convertApiGuess(apiGuess, index) {
  const guessAddress =
    index % 2 === 0 ? `#P2_${apiGuess.guess}` : `#P1_${apiGuess.guess}`;

  const newGuess = {
    guess: guessAddress,
    guessResult: apiGuess.guessResult,
    gameEnded: apiGuess.gameEnded,
  };
  return newGuess;
}
