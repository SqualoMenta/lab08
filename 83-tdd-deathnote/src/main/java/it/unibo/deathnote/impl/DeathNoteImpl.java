package it.unibo.deathnote.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private List<String> name = new LinkedList<>();
    private String deathCause;
    private String details;
    private long time;
    private static final long TIME_MAX = 40;

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
    public void writeName(String name) throws NullPointerException {
        if (Objects.isNull(name)) {
            throw new NullPointerException("Name is null");
        } else {
            this.name.add(name);
            this.time = System.currentTimeMillis();
        }
    }

    @Override
    public boolean writeDeathCause(String cause) throws IllegalStateException {
        if (Objects.isNull(cause) || Objects.isNull(this.name)) {

            if (Objects.isNull(cause)) {
                throw new IllegalStateException("no cause of death");
            } else {
                throw new IllegalStateException("no person to kill");
            }
        } else {
            if (System.currentTimeMillis() - this.time < TIME_MAX) {
                this.deathCause = cause;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

}
