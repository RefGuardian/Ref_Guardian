public class test6 {

    public void update(Collection<Snake> snakes) throws Exception {
    		synchronized (this.monitor) {
    			Location nextLocation = this.head.getAdjacentLocation(this.direction);
    			if (nextLocation.x >= SnakeUtils.PLAYFIELD_WIDTH) {
    				nextLocation.x = 0;
    			}
    			if (nextLocation.y >= SnakeUtils.PLAYFIELD_HEIGHT) {
    				nextLocation.y = 0;
    			}
    			if (nextLocation.x < 0) {
    				nextLocation.x = SnakeUtils.PLAYFIELD_WIDTH;
    			}
    			if (nextLocation.y < 0) {
    				nextLocation.y = SnakeUtils.PLAYFIELD_HEIGHT;
    			}
    			if (this.direction != Direction.NONE) {
    				this.tail.addFirst(this.head);
    				if (this.tail.size() > this.length) {
    					this.tail.removeLast();
    				}
    				this.head = nextLocation;
    			}
    
    			handleCollisions(snakes);
    		}
    	}
}
