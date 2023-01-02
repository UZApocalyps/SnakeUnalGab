import hevs.graphics.FunGraphics
import linkedList.LinkedList
object Main extends App {
  val direction = "I" // I = idle, L = gauche, D = bas, R = droit, U = haut

  //une linkedList va nous permettre de stocker les noeuds du serpent chaque noeud
  //contient les coordonnées de la case d'un morceau du serpent et la prochaine case vers laquelle
  //le serpent doit se déplacer
  //après chaque déplacement il faut utiliser la méthode shift() pour déplacer le serpent
  val snake = new LinkedList()

  val f = new FunGraphics(800, 800)
  val gameRunning = false;
  // Faire une grille qui sépare l'écran en case d
  // chaque case est un carré de 10 pixels par 10 pixels
  // chaque case est un objet de la classe cell

  private def start(): Unit = {
    // définir un point de départ random du serpent
  }

  private def stop(): Unit = {
    // Mettre la couleur de toutes les cellules en blancs et arrêter le jeu
  }

  private def gameLoop(): Unit = {
    // Boucle principale du jeu

    while (gameRunning) {
      // 1. Déplacer le serpent
      // 2. Vérifier si le serpent est mort
      // 3. Si le serpent est mort, arrêter le jeu
      // 4. Sinon, continuer le jeu

    }
  }

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

  private var cells :List[Cell] = List();
  private class Cell {
    var x = 0
    var y = 0
    var color = "white"
  }

  /**
   * Créé une grille de cellules en fonction de la taille de la fenêtre
   * @param windowHeight  taille de la fenêtre en pixels
   * @param windowWidth taille de la fenêtre en pixels
   * @param cellSize taille d'une cellule en pixels
  */
  def createGrid(windowHeight:Int,windowWidth:Int,cellSize:Int): Unit = {
    // Créer une grille de 80 cases par 80 cases
    for (i <- cellSize to windowHeight) {
      for (j <- cellSize to windowWidth) {
        val cell = new Cell()
        cell.x = i
        cell.y = j
        cells.appended(cell)
      }
    }
  }
}


