# MineSweeper-API 
## Deviget
#### This project was developed as a part of the hiring process for the role of "Senior Java Developer"

## Table of Contents
<!--ts-->
* [Table of Contents](#table-of-contents)
* [Dependencies](#dependencies)
* [Features](#features)
* [API Endpoints](#api-endpoints)
    * [POST - "/api/minesweeper/game/"](#post---apiminesweepergame)
    * [GET - "/api/minesweeper/game/{gameId}/"](#get---apiminesweepergamegameid)
    * [GET - "/api/minesweeper/game/cells/{gameId}/"](#get---apiminesweepergamecellsgameid)
    * [GET - "/api/minesweeper/game/pause/{gameId}/"](#get---apiminesweepergamepausegameid)
    * [GET - "/api/minesweeper/game/resume/{gameId}/"](#get---apiminesweepergameresumegameid)
    * [GET - "/api/minesweeper/cell/reveal/{cellId}/"](#get---apiminesweepercellrevealcellid)
    * [GET - "/api/minesweeper/cell/flag/{cellId}/"](#get---apiminesweepercellflagcellid)
<!--te-->

## Dependencies
* Lombok
* JPA
* H2 Database

## Features
- [x] Use Java as the main language
- [x] Design and implement a documented RESTful API for the game (think of a mobile app for your API)
- [x] When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
- [x] Ability to 'flag' a cell with a question mark or red flag
- [x] Detect when game is over
- [x] Persistence
- [x] Ability to start a new game and preserve/resume the old ones
- [x] Ability to select the game parameters: number of rows, columns, and mines
- [x] Add good documentation
- [ ] Add tests
- [ ] Time tracking
- [ ] Ability to support multiple users/accounts

## API Endpoints
### POST - "/api/minesweeper/game/"
* This endpoint is responsible for configuring a new game and saving the game and cells to the database. </br>
* If the parameters are invalid, should return an *HTTP status 400 - BAD REQUEST* and null in the body of the response. </br>
* If the Game creation is successful, should return an *HTTP status 201 - CREATED* and the Game object in the body of the response.

#### Sample payload:
```json
{
    "numOfRows": 5,
    "numOfColumns": 5,
    "numOfMines": 4
}
```

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/game/)

### GET - "/api/minesweeper/game/{gameId}/"
* This endpoint is responsible for returning a game by its id. </br>
* If the Game was not found, should return an *HTTP status 404 - NOT FOUND* and null in the body of the response. </br>
* If the Game is found, should return an *HTTP status 200 - OK* and the Game object in the body of the response.

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/game/{gameId}/)

### GET - "/api/minesweeper/game/cells/{gameId}/"
* This endpoint is responsible for returning all the cells of a game by the game id. </br>
* If the Game was not found, should return an *HTTP status 404 - NOT FOUND* and null in the body of the response. </br>
* If the Game is found, should return an *HTTP status 200 - OK* and a list of the cell objects in the body of the response.

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/game/cells/{gameId}/)

### GET - "/api/minesweeper/game/pause/{gameId}/"
* This endpoint is responsible for pausing a game, updating the status of the Game to "paused". </br>
* If the Game status is closed, should return an *HTTP status 400 - BAD REQUEST* and null in the body of the response. </br>
* If the Game was already paused, should return an *HTTP status 200 - OK* and the Game object in the body of the response. </br>
* If the Game was successfuly paused, should return an *HTTP status 200 - OK* and the Game object in the body of the response.

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/game/pause/{gameId}/)

### GET - "/api/minesweeper/game/resume/{gameId}/"
* This endpoint is responsible for resuming a game, updating the status of the Game to "open". </br>
* If the Game status is closed, should return an *HTTP status 400 - BAD REQUEST* and null in the body of the response. </br>
* If the Game was already open, should return an *HTTP status 200 - OK* and the Game object in the body of the response. </br>
* If the Game was successfuly resumed, should return an *HTTP status 200 - OK* and the Game object in the body of the response.

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/game/resume/{gameId}/)

### GET - "/api/minesweeper/cell/reveal/{cellId}/"
* This endpoint is responsible for revealing a Cell by its id, updating the status of the Cell. </br>
* If the Cell's Game status is closed, should return an *HTTP status 400 - BAD REQUEST* and null in the body of the response. </br>
* If the Cell is a Mine, should end the game and return an *HTTP status 200 - OK* and the Game object in the body of the response. </br>
* If the Cell was covered (or flagged), should reveal the Cell (and all of the adjacent cells - if the "numOfAdjacentMines" is 0) and return an *HTTP status 200 - OK* and the Game object in the body of the response. </br>
* If the Cell was already uncovered, should return an *HTTP status 200 - OK* and the Game object in the body of the response. </br>

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/cell/reveal/{cellId}/)

### GET - "/api/minesweeper/cell/flag/{cellId}/"
* This endpoint is responsible for flagging a Cell by its id, updating the status of the Cell. </br>
* If the Cell's Game status is closed, should return an *HTTP status 400 - BAD REQUEST* and null in the body of the response. </br>
* If the Cell is covered, should flag the Cell and return an *HTTP status 200 - OK* and the Cell object in the body of the response. </br>
* If the Cell is flagged, should unflag the Cell and return an *HTTP status 200 - OK* and the Cell object in the body of the response. </br>
* If the Cell is uncovered, should return an *HTTP status 400 - BAD REQUEST* and null in the body of the response. </br>

#### Endpoint URL
This endpoint API is available at [this link](https://minesweeperapi-deviget.herokuapp.com/api/minesweeper/cell/flag/{cellId}/)