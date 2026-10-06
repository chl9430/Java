package save;

import unit.Archer;
import unit.Element;
import unit.Hero;
import unit.Mage;
import unit.Warrior;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 영웅을 파일에 저장하고 불러온다 (파일 입출력)
 * 형식: 한 줄에 하나씩  이름 / 직업 / 특성 / 레벨 / 경험치
 */
public class SaveService {
    private final Path path;

    public SaveService(Path path) {
        this.path = path;
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public void save(Hero hero) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(path)) {   // 자동으로 close
            w.write(hero.getName() + "\n");
            w.write(hero.getJobName() + "\n");
            w.write(hero.getElement().name() + "\n");
            w.write(hero.getLevel() + "\n");
            w.write(hero.getExp() + "\n");
        }
    }

    public Hero load() throws IOException {
        List<String> v = Files.readAllLines(path);
        Element element = Element.valueOf(v.get(2));
        Hero hero = switch (v.get(1)) {
            case "마법사" -> new Mage(v.get(0), element);
            case "궁수" -> new Archer(v.get(0), element);
            default -> new Warrior(v.get(0), element);
        };
        hero.restore(Integer.parseInt(v.get(3)), Integer.parseInt(v.get(4)));
        return hero;
    }
}
