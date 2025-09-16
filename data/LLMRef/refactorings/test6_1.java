public class test6 {

    public void update(Collection<Snake> snakes) throws Exception {
        synchronized (this.monitor) {
            Location nextLocation = this.head.getAdjacentLocation(this.direction);

            normalizeLocation(nextLocation);

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

    private void normalizeLocation(Location location) {
        if (location.x >= SnakeUtils.PLAYFIELD_WIDTH) {
            location.x = 0;
        }
        if (location.y >= SnakeUtils.PLAYFIELD_HEIGHT) {
            location.y = 0;
        }
        if (location.x < 0) {
            location.x = SnakeUtils.PLAYFIELD_WIDTH;
        }
        if (location.y < 0) {
            location.y = SnakeUtils.PLAYFIELD_HEIGHT;
        }
    }
}
