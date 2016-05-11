import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class BookPane extends JPanel
{
  Hero hero;
  Game game;
  static int cnt;
  static String[] info = new String[32];
  static int[] imgtmp = new int[32];
  static final String ATT = "名字            生命   攻击  防御  伤害1";

  BookPane(Hero paramHero, Game paramGame)
  {
    this.hero = paramHero;
    this.game = paramGame;
  }

  int judgeDamage(Hero paramHero, int paramInt) {
    paramInt--;
    Monster localMonster = new Monster(Game.MNAME[paramInt], Game.MHP[paramInt], Game.MATK[paramInt], Game.MDEF[paramInt], Game.MMONEY[paramInt], Game.MEXP[paramInt]);

    int i = localMonster.hp;
    int j = localMonster.atk;
    int k = localMonster.def;
    int m = paramHero.getHp();
    int n = paramHero.getAtk();
    int i1 = paramHero.getDef();
    int i2 = 0;
    if (n <= k) { i2 = 9999999;
    } else if (j <= i1) { i2 = 0;
    } else {
      while (true) {
        int i3 = n - k;
        int i4 = j - i1;
        i -= i3;
        if (i <= 0) break;
        m -= i4;
      }
      i2 = paramHero.getHp() - m;
    }
    return i2;
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    Image localImage1 = new ImageIcon("pic/bg.png").getImage();
    paramGraphics.drawImage(localImage1, 0, 0, this);
    paramGraphics.setColor(Color.CYAN);
    paramGraphics.drawRect(0, 0, 299, 299);
    paramGraphics.setFont(new Font("TimesRoman", 0, 17));
    paramGraphics.drawString("名字            生命   攻击  防御  伤害", 0, 32);
    for (int i = 0; i < cnt; i++) {
      Image localImage2 = new ImageIcon("pic/m" + (imgtmp[i] + 3) / 4 + ".png").getImage();
      int j = 0;
      int k = (i + 1) * 32;
      int m = 0;
      int n = (imgtmp[i] - 1) % 4 * 32;
      paramGraphics.drawImage(localImage2, j, k, j + 32, k + 32, m, n, m + 32, n + 32, this);
      paramGraphics.drawString(info[i], 32, 32 * (i + 2));
    }
  }

  public void showInfo() {
    HashSet<Integer> localHashSet = new HashSet<Integer>();
    for (int i = 0; i < 10; i++)
      for (int j = 0; j < 10; j++)
        if (this.game.mt[(this.game.curfloor - 1)][i][j] > 0)
          localHashSet.add(Integer.valueOf(this.game.mt[(this.game.curfloor - 1)][i][j]));
    cnt = 0;
    for (Integer localInteger : localHashSet) {
      int k = judgeDamage(this.hero, localInteger.intValue());
      String str = String.format("%s%5s%5s%5s%5s", new Object[] { Game.MNAME[(localInteger.intValue() - 1)], Integer.valueOf(Game.MHP[(localInteger.intValue() - 1)]), Integer.valueOf(Game.MATK[(localInteger.intValue() - 1)]), Integer.valueOf(Game.MDEF[(localInteger.intValue() - 1)]), Integer.valueOf(k) });

      info[(cnt++)] = str;
      imgtmp[(cnt - 1)] = localInteger.intValue();
    }
    repaint();
  }
}