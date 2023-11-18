package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private DeathList victims = new DeathList();
    private long time;

    private static final long TIME_MAX_CAUSE = 40;
    private static final long TIME_MAX_DETAILS = 6040;
    private static final int CAUSE = 0;
    private static final int DETAILS = 1;

    @Override
    public String getRule(int ruleNumber) throws IllegalArgumentException {
        try {
            String rule = DeathNote.RULES.get(ruleNumber - 1);
            return rule;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("no such a rule number");
        }
    }

    @Override
    public void writeName(String name) {
        this.victims.put(name);
        this.update();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (Objects.isNull(cause)) {
            throw new IllegalStateException("no cause of death");
        } else if (victims.isEmpty()) {
            throw new IllegalStateException("no person to kill");
        }

        if (System.currentTimeMillis() - this.time < TIME_MAX_CAUSE) {
            victims.changeCause(cause);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeDetails(String details) {
        if (Objects.isNull(details)) {
            throw new IllegalStateException("no details of death");
        } else if (victims.isEmpty()) {
            throw new IllegalStateException("no person to kill");
        }

        if (System.currentTimeMillis() - this.time < TIME_MAX_DETAILS) {
            victims.changeDetails(details);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathCause(String name) {
        try {
            return this.victims.get(name).get(CAUSE);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("the passed name was not in the book");
        }
    }

    @Override
    public String getDeathDetails(String name) {
        try {
            return this.victims.get(name).get(DETAILS);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("the passed name was not in the book");
        }
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.victims.containsKey(name);
    }

    private void update() {
        this.time = System.currentTimeMillis();
    }

    private class DeathList extends HashMap<String, ArrayList<String>> {
        private String lastName = "";

        void put(String name) {
            if (Objects.isNull(name)) {
                throw new NullPointerException("Name is null");
            } else {
                lastName = name;
                ArrayList<String> MyList = new ArrayList<>(List.of("Heath Attack", ""));
                this.put(name, MyList);
            }
        }

        void changeCause(String newCause) {
            this.get(lastName).set(0, newCause);
        }

        void changeDetails(String newDetails) {
            this.get(lastName).set(1, newDetails);
        }
    }

}
