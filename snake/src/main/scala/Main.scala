import hevs.graphics.FunGraphics
import scala.util.Random
import java.awt.Color
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import hevs.graphics.utils.GraphicsBitmap

/** Contient les paramètres de la partie
  */
object Settings {
  val cellSize = 20
  val windowWidth = 800
  val windowHeight = 800
  val snakeBaseSpeed = 1000
}

object Main extends App {

  var direction = "I" // I = idle, L = gauche, D = bas, R = droit, U = haut

  val f =
    new FunGraphics(Settings.windowWidth, Settings.windowHeight, "Snake Game")

  val foodTimer = new java.util.Timer()

  val foodTask = new java.util.TimerTask {
    def run() = {
      generateFood()
    }
  }

  foodTimer.schedule(foodTask, 2000L, 2000L)

  f.setKeyManager(
    new KeyAdapter() { // Will be called when a key has been pressed
      override def keyPressed(e: KeyEvent): Unit = {
        if (e.getKeyCode == KeyEvent.VK_LEFT && direction != "R")
          direction = "L"
        if (e.getKeyCode == KeyEvent.VK_RIGHT && direction != "L")
          direction = "R"
        if (e.getKeyCode == KeyEvent.VK_UP && direction != "D")
          direction = "U"
        if (e.getKeyCode == KeyEvent.VK_DOWN && direction != "U")
          direction = "D"
        if (e.getKeyChar == 'r') {
          println("reset")
          gameRunning = false;
        }
      }
    }
  )

  var snake = new SnakeList()

  var gameRunning = false;

  val grid = CellManager.createGrid(f.height, f.width, Settings.cellSize)

  start();

  private def start(): Unit = {

    // case de début du serpent (première case qui pourrait correspondre au centre)
    var startCell: CellManager.Cell = CellManager
      .getAllCells()
      .find(c =>
        (c.x <= f.width / 2 && c.x >= f.width / 2 - Settings.cellSize) && c.y <= f.height / 2 && c.y >= f.height / 2 - Settings.cellSize
      )
      .get;

    snake.addNodes(startCell)
    gameRunning = true

    foodTask.run()
    gameLoop()
    restart()

  }

  private def restart(): Unit = {
    clearWindow()
    for (cell <- CellManager.getAllCells()) {
      cell.color = "white"
    }
    snake = new SnakeList()
    direction = "I"
    start()
  }

  private def gameLoop(): Unit = {
    while (gameRunning) {
      clearWindow()
      moveSnake()
      f.frontBuffer.synchronized {

        // 1. Dessiner l'arrière plan
        for (cell <- CellManager.getAllCells()) {
          f.setColor(Color.GREEN)
          f.drawFillRect(cell.x, cell.y, Settings.cellSize, Settings.cellSize)
        }
        // 2. Dessiner le serpent
        for (cell <- snake.nodes) {
          f.setColor(Color.BLACK)
          f.drawFillRect(cell.x, cell.y, Settings.cellSize, Settings.cellSize)
        }

        // 3. Dessiner les fruits
        for (cell <- CellManager.getAllCells().filter(k => k.color == "Red")) {
          f.setColor(Color.RED)
          f.drawFillRect(cell.x, cell.y, Settings.cellSize, Settings.cellSize)
        }
      }

      Thread.sleep(1 + Settings.snakeBaseSpeed / snake.nodes.length)
    }
  }

  private def tick(): Unit = {
    moveSnake();
  }

  private def moveSnake(): Unit = {
    try {
      // Déplacer le serpent dans la prochaine case en fonction de la direction
      if (snake.nodes.length >= 1) {
        var nextCell = CellManager.nextCellByDirection(
          snake.nodes(0),
          direction
        )

        if (nextCell != null) {
          if (snake.in(nextCell)) {
            lost();
          } else {
            if (nextCell.color == "Red") {
              nextCell.color = "Black"
              snake.addNodes(nextCell)
            }
            snake.addAtStart(nextCell)
            snake.removeLast()
          }

        } else {
          println("NULL")
        }

      }
    } catch {
      case e: Exception =>
        println("error")
        lost()
    }

  }
  private def clearWindow() {
    for (c <- CellManager.getAllCells()) {
      if (c.color == "Black") {
        c.color = "white";
      }
    }
    f.clear()

  }
  private def lost(): Unit = {

    f.clear();
    val bm = new GraphicsBitmap("/res/game_over.jpg")
    f.drawPicture(f.width / 2, f.height / 2, bm)
    println("You lose !")
    gameRunning = false;
    Thread.sleep(3000);
    restart()

  }
  private def generateFood(): Unit = {
    // Générer de la nourriture aléatoirement sur la grille
    // La nourriture ne doit pas appraître sur le serpent
    var a = CellManager.getAllCells()
    var b: List[CellManager.Cell] = List()

    for (cell <- a) {
      if (
        snake.nodes
          .filter(n => n.x == cell.x && n.y == cell.y)
          .length <= 0
      ) {
        b = cell :: b // Ajoute un element a la liste.
      }
    }

    var fruitCell = b(Random.nextInt(b.length))
    fruitCell.color = "Red"

  }
}

object CellManager {

  private var cells: List[Cell] = List();
  private var cellSize: Int = 10;
  class Cell {
    var x = 0
    var y = 0
    var color = "white"
  }

  /** Créé une grille de cellules en fonction de la taille de la fenêtre
    * @param windowHeight
    *   taille de la fenêtre en pixels
    * @param windowWidth
    *   taille de la fenêtre en pixels
    * @param Settings.cellSize
    *   taille d'une cellule en pixels
    */
  def createGrid(windowHeight: Int, windowWidth: Int, cellSize: Int): Unit = {
    // Créer une grille de 80 cases par 80 cases
    this.cellSize = cellSize;
    for (i <- 0 to windowHeight by Settings.cellSize) {
      for (j <- 0 to windowWidth by Settings.cellSize) {
        val cell = new Cell()
        cell.x = i
        cell.y = j
        cells = cell :: cells
      }
    }
  }

  /** @return
    *   Toutes les cellules de la grille
    */
  def getAllCells(): List[Cell] = {
    return cells
  }

  /** Donne la prochaine case en fonction de la direction
    *
    * @param cell
    * @param direction
    *   I = idle, L = gauche, D = bas, R = droit, U = haut
    * @return
    */
  def nextCellByDirection(cell: Cell, direction: String): Cell = {
    var nextcell: Cell = null;

    direction match {
      case "I" => nextcell = null
      case "L" =>
        nextcell = cells
          .filter(c => c.x == cell.x - Settings.cellSize && c.y == cell.y)
          .head
      case "D" =>
        nextcell = cells
          .filter(c => c.x == cell.x && c.y == cell.y + Settings.cellSize)
          .head
      case "R" =>
        nextcell = cells
          .filter(c => c.x == cell.x + Settings.cellSize && c.y == cell.y)
          .head
      case "U" =>
        nextcell = cells
          .filter(c => c.x == cell.x && c.y == cell.y - Settings.cellSize)
          .head

    }

    return nextcell
  }

  def changeColor(cell: Cell, color: String): Unit = {
    cell.color = color

  }
}

class SnakeList {
  var nodes: List[CellManager.Cell] = List();

  def addNodes(cell: CellManager.Cell) = {
    nodes = cell :: nodes
  }

  def removeLast() {
    nodes = nodes.dropRight(1)
  }

  def addAtStart(cell: CellManager.Cell) {
    var temporary: List[CellManager.Cell] = List()

    for (i <- nodes.length - 1 to 0 by -1) {
      temporary = nodes(i) :: temporary
    }

    temporary = cell :: temporary;

    nodes = temporary
  }

  /** Vérifie si la cellule donné est dans la liste (en fonction de sa position)
    *
    * @param cell
    *   Cellule
    * @return
    *   True si la cellule se trouve dans la liste
    */
  def in(cell: CellManager.Cell): Boolean = {
    for (n <- nodes) {
      if (n.x == cell.x && n.y == cell.y) {
        return true;
      }
    }
    return false
  }

}
