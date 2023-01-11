import hevs.graphics.FunGraphics
import linkedList.LinkedList
import scala.util.Random
import java.awt.Color
object Main extends App {
  val direction = "I" // I = idle, L = gauche, D = bas, R = droit, U = haut

  // Ce timer va appeler la méthode Tick
  val tickTimer = new java.util.Timer()
  val task = new java.util.TimerTask {
    def run() = {
      tick()
    }
  }
  tickTimer.schedule(task, 1000L, 1000L)

  // une linkedList va nous permettre de stocker les noeuds du serpent chaque noeud
  // contient les coordonnées de la case d'un morceau du serpent et la prochaine case vers laquelle
  // le serpent doit se déplacer
  // après chaque déplacement il faut utiliser la méthode shift() pour déplacer le serpent
  val snake = new LinkedList()

  val f = new FunGraphics(800, 800)
  var gameRunning = false;
  // Faire une grille qui sépare l'écran en case d
  // chaque case est un carré de 10 pixels par 10 pixels
  // chaque case est un objet de la classe cell

  // Define the size of the grid (number of rows and columns)
  val rows: Int = f.width / 10
  val cols: Int = f.height / 10

  // Create a 2 dimension array of Cell objects
  val grid = CellManager.createGrid(f.height, f.width, 10)
  start();

  private def start(): Unit = {
    // définir un point de départ random du serpent
    val randomLength = CellManager.getAllCells().length
    val random = new Random().nextInt(randomLength)

    // case de début du serpent
    var startCell: CellManager.Cell = CellManager.getAllCells()(random);

    snake.addNode(startCell.x, startCell.y)
    gameRunning = true; gameRunning = true;

    gameLoop();
    task.run()

  }

  private def stop(): Unit = {
    // Mettre la couleur de toutes les cellules en blancs et arrêter le jeu
    gameRunning = false;
  }

  private def gameLoop(): Unit = {
    // Boucle principale du jeu
    while (gameRunning) {
      f.frontBuffer.synchronized {
        f.clear()
        // 1. Dessiner le serpent
        for (cell <- this.snake.nodes) {
          f.setColor(Color.BLACK)
          f.drawFillRect(cell.xCoord, cell.yCoord, 10, 10)
        }
      }

    }
  }
  private def tick(): Unit = {}

  private def moveSnake(): Unit = {
    // Déplacer le serpent dans la prochaine case en fonction de la direction
  }

  private def updateDirection(): Unit = {
    // Mettre à jour la direction du serpent en fonction des inputs du joueur
  }

  private def generateFood(): Unit = {
    // Générer de la nourriture aléatoirement sur la grille
    // La nourriture ne doit pas appraître sur le serpent

  }

  private def checkIfSnakeIsDead(): Boolean = {
    // Vérifier si le serpent est mort
    // Le serpent est mort si il touche un mur ou si il se touche lui-même
    // on peut regarder si le serpent se touche en regardant si la case "next" de l'élement suivant
    // est déjà occupé par une autre case du serpent
    return false
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
    * @param cellSize
    *   taille d'une cellule en pixels
    */
  def createGrid(windowHeight: Int, windowWidth: Int, cellSize: Int): Unit = {
    // Créer une grille de 80 cases par 80 cases
    this.cellSize = cellSize;
    for (i <- cellSize to windowHeight) {
      for (j <- cellSize to windowWidth) {
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
      case "I" => nextcell = cell
      case "L" =>
        nextcell =
          cells.filter(c => c.x == cell.x - cellSize && c.y == cell.y).head
      case "D" =>
        nextcell =
          cells.filter(c => c.x == cell.x && c.y == cell.y + cellSize).head
      case "R" =>
        nextcell =
          cells.filter(c => c.x == cell.x + cellSize && c.y == cell.y).head
      case "U" =>
        nextcell =
          cells.filter(c => c.x == cell.x && c.y == cell.y - cellSize).head

    }

    return nextcell
  }

  def changeColor(cell: Cell, color: String): Unit = {
    cell.color = color

  }
}
