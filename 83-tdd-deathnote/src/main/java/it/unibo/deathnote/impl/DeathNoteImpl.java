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
        return writeSomething(cause, Writer.CAUSE);
    }

    @Override
    public boolean writeDetails(String details) {
        return writeSomething(details, Writer.DETAILS);
    }

    private boolean writeSomething(String something, final Writer whatDo) {
        if (Objects.isNull(something)) {
            throw new IllegalStateException("nothing passed");
        } else if (victims.isEmpty()) {
            throw new IllegalStateException("no person to kill");
        }

        if (System.currentTimeMillis()
                - this.time < (whatDo.equals(Writer.CAUSE) ? TIME_MAX_CAUSE : TIME_MAX_DETAILS)) {
            if (whatDo.equals(Writer.CAUSE)) {
                victims.changeCause(something);
            } else {
                victims.changeDetails(something);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathCause(String name) {
        try {
            return this.victims.get(name).get(Writer.CAUSE.ordinal());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("the passed name was not in the book");
        }
    }

    @Override
    public String getDeathDetails(String name) {
        try {
            return this.victims.get(name).get(Writer.DETAILS.ordinal());
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

    private enum Writer {
        CAUSE,
        DETAILS
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
