package linkedList
class LinkedList {
  var nodes: List[Node] = List()
  var head: Node = null
  var tail: Node = null

  /** Déplace chaque noeud dans la case précédente
    */
  def shift(xCoord: Int, yCoord: Int): Unit = {

    try {
// shift all nodes
      var newnodesList: List[Node] = List()
      val newHead = new Node()
      this.head = newHead
      newHead.xCoord = xCoord
      newHead.yCoord = yCoord
      newnodesList = newHead :: newnodesList
      println(nodes.length)
      val lengthOfNodes = nodes.length
      for (i <- 0 until nodes.length - 1) {
        nodes(i).xCoord = nodes(i + 1).xCoord
        nodes(i).yCoord = nodes(i + 1).yCoord
        newnodesList = nodes(i) :: newnodesList
      }
      tail = newnodesList.last
      nodes = newnodesList
      println(newnodesList.length)
    } catch {
      case e: Exception => println("Exception caught: " + e);
    }

  }

  /** Ajoute un noeud dans la liste
    */
  def addNode(xCoord: Int, yCoord: Int): Unit = {
    // Ajouter un noeud à la fin du serpent
    val newNode = new Node()
    newNode.xCoord = xCoord
    newNode.yCoord = yCoord
    if (tail != null) {
      tail.next = newNode
    } else {
      tail = newNode
      head = newNode
    }
    nodes = newNode :: nodes
  }
}

class Node {
  var xCoord: Int = 0
  var yCoord: Int = 0
  var next: Node = null
}
