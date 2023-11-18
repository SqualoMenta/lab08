package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {

    private DeathNote firstNote;
    private static final long NO_MORE_CAUSES = 100;
    private static final long NO_MORE_DETAILS = 6100;

    @BeforeEach
    void init() {
        firstNote = new DeathNoteImpl();
    }

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
        assertFalse(firstNote.isNameWritten(""), "a blanck name is in the note for no reason");
        firstNote.writeName("null");
        assertTrue(firstNote.isNameWritten("Bitten Fra"));
        assertTrue(firstNote.isNameWritten("null"));
    }

    @Test
    public void writeDeathCause() throws InterruptedException {
        firstNote.writeName("Bitten Fra");
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
        try {
            firstNote.getDeathCause("Baldo");
            fail("the passed name was not in the book");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "the passed name was not in the book");
        }
        assertEquals("Heath Attack", firstNote.getDeathCause("Bitten Fra"));
        firstNote.writeName("Baldo");
        firstNote.writeDeathCause("karting accident");
        assertEquals("karting accident", firstNote.getDeathCause("Baldo"));
        Thread.sleep(NO_MORE_CAUSES);
        assertFalse(firstNote.writeDeathCause("Death"));
        assertEquals(firstNote.getDeathCause("Baldo"), "karting accident");
    }

    @Test
    public void TestWriteDetails(String details) throws InterruptedException {
        firstNote.writeName("NullBoy");
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
            assertEquals(e.getMessage(), "no details of death");
        }
        try {
            firstNote.getDeathDetails("Null");
            fail("the passed name was not in the book");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "the passed name was not in the book");
        }
        assertEquals(firstNote.getDeathDetails("NullBoy"), "");
        assertTrue(firstNote.writeDetails("run for to long"));
        assertEquals(firstNote.getDeathDetails("NullBoy"), "run for to long");
        firstNote.writeName("L");
        Thread.sleep(NO_MORE_DETAILS);
        assertFalse(firstNote.writeDetails("run for to long"));
        assertEquals(firstNote.getDeathDetails("L"), "");
    }
}