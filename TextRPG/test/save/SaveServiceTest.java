package save;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import unit.Element;
import unit.Hero;
import unit.Mage;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveServiceTest {
    @TempDir
    Path dir;

    @Test
    void 저장한_영웅을_그대로_불러온다() throws IOException {
        SaveService service = new SaveService(dir.resolve("save.txt"));
        assertFalse(service.exists());

        Hero hero = new Mage("아서", Element.WATER);
        hero.gainExp(40);              // 레벨 2로 상승
        service.save(hero);

        assertTrue(service.exists());
        Hero loaded = service.load();
        assertEquals("아서", loaded.getName());
        assertEquals("마법사", loaded.getJobName());
        assertEquals(Element.WATER, loaded.getElement());
        assertEquals(hero.getLevel(), loaded.getLevel());
        assertEquals(hero.getExp(), loaded.getExp());
    }
}
