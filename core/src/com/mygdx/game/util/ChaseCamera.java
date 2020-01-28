package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Camera;
import com.mygdx.game.entities.Mario;

public class ChaseCamera {

    public Camera camera;
    public Mario target;

    public ChaseCamera(com.badlogic.gdx.graphics.Camera camera, Mario target) {
        this.camera = camera;
        this.target = target;
    }

    public void update() {
        camera.position.x = target.position.x;
        camera.position.y = 240;
    }
}
