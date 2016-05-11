import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class StatePane extends JPanel
{
  Hero hero;
  Game game;
  static final String[] ATT = { "楼层数:", "生命值:", "攻击力:", "防御力:", "金钱数:", "经验值:", "黄钥匙:", "蓝钥匙:", "红钥匙:", "最高层:" };

  StatePane(Hero paramHero, Game paramGame)
  {
    this.hero = paramHero;
    this.game = paramGame;
  }

  public void paint(Graphics paramGraphics)
  {
    super.paint(paramGraphics);
    Image localImage = new ImageIcon("pic/bg.png").getImage();
    paramGraphics.drawImage(localImage, 0, 0, this);
    paramGraphics.setColor(Color.CYAN);
    paramGraphics.drawRect(0, 0, 199, 319);
    paramGraphics.setColor(Color.GREEN);
    paramGraphics.setFont(new Font("TimesRoman", 0, 27));
    int[] arrayOfInt = { this.game.curfloor, this.hero.getHp(), this.hero.getAtk(), this.hero.getDef(), this.hero.getMoney(), this.hero.getExp(), this.hero.getYkey(), this.hero.getBkey(), this.hero.getRkey(), this.game.maxfloor };

    for (int i = 0; i < 10; i++) {
      String str = ATT[i] + arrayOfInt[i];
      paramGraphics.drawString(str, 0, 32 * (i + 1) - 5);
    }
  }
}