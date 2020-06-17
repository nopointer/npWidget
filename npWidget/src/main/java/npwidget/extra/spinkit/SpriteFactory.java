package npwidget.extra.spinkit;


import npwidget.extra.spinkit.sprite.Sprite;
import npwidget.extra.spinkit.style.ChasingDots;
import npwidget.extra.spinkit.style.Circle;
import npwidget.extra.spinkit.style.CubeGrid;
import npwidget.extra.spinkit.style.DoubleBounce;
import npwidget.extra.spinkit.style.FadingCircle;
import npwidget.extra.spinkit.style.FoldingCube;
import npwidget.extra.spinkit.style.MultiplePulse;
import npwidget.extra.spinkit.style.MultiplePulseRing;
import npwidget.extra.spinkit.style.Pulse;
import npwidget.extra.spinkit.style.PulseRing;
import npwidget.extra.spinkit.style.RotatingCircle;
import npwidget.extra.spinkit.style.RotatingPlane;
import npwidget.extra.spinkit.style.ThreeBounce;
import npwidget.extra.spinkit.style.WanderingCubes;
import npwidget.extra.spinkit.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
