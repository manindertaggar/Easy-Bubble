package com.dhankher;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Main {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"com.eworl.easybubble.db");
        Entity program = schema.addEntity("program");
        program.addIdProperty();
        program.addStringProperty("appName");
        program.addIntProperty("appIcon");
        DaoGenerator dg = new DaoGenerator();
        dg.generateAll(schema,"./app/src/main/java");
    }
}