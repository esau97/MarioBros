package com.mygdx.game.util;

public class Enums {


    public enum Direction {
        LEFT, RIGHT
    }
    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED
    }
    public enum Facing {
        LEFT,
        RIGHT
    }
    public enum WalkState {
        STANDING,
        WALKING
    }

    public enum MarioState {
        MARIO,
        SUPERMARIO,
        DEATH
    }

    public enum CoinState {
        SHOW,
        HIDE
    }
    public enum MushState {
        SHOW,
        HIDE
    }
}
