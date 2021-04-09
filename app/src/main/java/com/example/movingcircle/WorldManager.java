package com.example.movingcircle;

import java.util.ArrayList;

public class WorldManager {

    private ArrayList<WorldEntity> worldEntities;

    public WorldManager() {
        this.worldEntities = new ArrayList<>();
    }

    public ArrayList<WorldEntity> getWorldEntities() {
        return worldEntities;
    }
}
