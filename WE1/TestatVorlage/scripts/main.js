import { gameService } from './model/game-service.js';

// Dummy Code
console.log('isOnline:', gameService.isOnline);

const rankings = await gameService.getRankings();

Object.values(rankings).forEach(x => {
    console.log(JSON.stringify(x));
});

console.log(await gameService.evaluate('Michael', gameService.possibleHands[0]));

//State
let playerName = ""

//elements
let elStartPage = document.getElementById("start-page")
let elGamePage = document.getElementById("game-page")
let elBtnStartGame = document.getElementById("start-game")
let elBtnBackToStart = document.getElementById("back-to-start")
let elInputName = document.getElementById("name-input")
let elPlayerNameDisplay = document.getElementById("player-name-display")

//Functions
const switchPage = () => {
    elStartPage.hidden = !elStartPage.hidden
    elGamePage.hidden = !elGamePage.hidden
}

const startGame = (e) => {
    console.log("Starting game")
    let inputValue = e.target.value
    // if (inputValue.trim() == "") return
    playerName = inputValue
    elPlayerNameDisplay.innerHTML = playerName
    switchPage()
}


//Event Listeners
document.addEventListener("DOMContentLoaded", () => {
    elBtnBackToStart.addEventListener("click", switchPage);
    elBtnStartGame.addEventListener("click", startGame)
})
