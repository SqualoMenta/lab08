package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;


class TestDeathNote {

    DeathNote firstNote = new DeathNoteImpl();

    @Test
    public void testGetRule() {
        try {
            firstNote.getRule(0);
            fail("Text shuldn't accept rule 0");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().equals("no such a rule number"));
        }
        try {
            firstNote.getRule(DeathNote.RULES.size() + 1);
            fail("Text shuldn't accept that rule");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().equals("no such a rule number"));
        }
    }

    public void testRuleValiduty() {
        for (String rule : DeathNote.RULES) {
            assertFalse(Objects.isNull(rule), "found a null rule");
            assertFalse(rule.equals(""), "found a blanck rule");
        }
    }

    @Test
    public void testWriteName() {
        try {
            firstNote.writeName(null);
            fail();
        } catch (NullPointerException e) {
            assertEquals(e.getMessage(), "Name is null");
        }
        if (firstNote.isNameWritten("Bitten Fra")) {
            fail("name was not written, but test said it was");
        } else {
            firstNote.writeName("Bitten Fra");
        }
        if (!firstNote.isNameWritten("Bitten Fra")) {
            fail("name was not written, but it shuold have been");
        }
        assertFalse(firstNote.isNameWritten(""), "a blanck name is in the note for no reason")
    }

    @Test
    public void writeDeathCause() throws InterruptedException {
        try {
            DeathNote secondNote = new DeathNoteImpl();
            secondNote.writeDeathCause("Death of Death");
            fail("not specified the person that is death, but nothing went wrong");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "no person to kill");
        }
        try {
            firstNote.writeDeathCause(null);
            fail("not specified the cause death, but nothing went wrong");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "no cause of death");
        }
        assertEquals("Heath Attack", firstNote.getDeathCause("Bitten Fra"));
        firstNote.writeName("Baldo");
        firstNote.writeDeathCause("karting accident");
        assertEquals("karting accident", firstNote.getDeathCause("Baldo"));
        Thread.sleep(100);
        assertEquals(-1, firstNote.writeDeathCause("Death"));
        assertEquals(firstNote.getDeathCause("Baldo"), "karting accident");
    }

    @Test
    public void TestWriteDetails(String details) {
        try {
            DeathNote secondNote = new DeathNoteImpl();
            secondNote.writeDetails("Death after Death");
            fail("not specified the person that is death, but nothing went wrong");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "no person to kill");
        }
        try {
            firstNote.writeDeathCause(null);
            fail("not specified the cause death, but nothing went wrong");
        } catch (IllegalStateException e) {
            assertEquals(e.getMessage(), "no cause of death");
        }
    }

    @Test
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathCause'");
    }

    @Test
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Test
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }

}