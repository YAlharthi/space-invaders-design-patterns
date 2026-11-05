import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * 
 * @author
 */
public class Sprite {

        private boolean visible;
        private SpriteGraphic graphic;
        protected int x;
        protected int y;
        protected boolean dying;
        protected int dx;

        /*
         * Constructor
         */
        public Sprite() {
            visible = true;
        }

        public void die() {
            visible = false;
        }

        public boolean isVisible() {
            return visible;
        }

        protected void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setGraphic(SpriteGraphic graphic) {
            this.graphic = graphic;
        }

        /* The flyweight is shared, so this sprite supplies its own position. */
        public void draw(Graphics g, ImageObserver observer) {
            if (graphic != null) {
                graphic.draw(g, x, y, observer);
            }
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public void setDying(boolean dying) {
            this.dying = dying;
        }

        public boolean isDying() {
            return this.dying;
        }
}