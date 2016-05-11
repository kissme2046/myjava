import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MyPanel extends JPanel
  implements KeyListener
{
  Game game;
  boolean musicflag2;
  boolean musicflag3;
  boolean musicflag4;
  boolean musicflag5;
  boolean musicflag6;
  Hero hero;
  StatePane sp;
  BookPane bp;
  int monstercnt;
  int doorcnt;
  int herocnt;
  Point doorxy;
  Point heroxy;
  boolean isopeningdoor;
  boolean ismoving;
  boolean cheath;
  boolean cheate;
  boolean cheatr;
  boolean cheato;
  boolean usingbook;
  boolean usingstate;
  MagicTower mt;
  JFrame book;
  JFrame state;

  MyPanel(Hero paramHero, Game paramGame, StatePane paramStatePane, BookPane paramBookPane)
  {
    this.hero = paramHero;
    this.game = paramGame;
    this.sp = paramStatePane;
    this.bp = paramBookPane;
  }

  public void paint(Graphics paramGraphics) {
    super.paint(paramGraphics);
    if (this.doorcnt >= 4) this.hero.openedDoor(this.doorxy);
    drawEdge(paramGraphics);
    drawLand(paramGraphics);
    drawMap(paramGraphics);
    drawHero(paramGraphics);
    if (!this.ismoving) this.herocnt = 0; 
  }

  public void keyPressed(KeyEvent paramKeyEvent)
  {
    if ((this.isopeningdoor) || (this.ismoving)) return;
    Point localPoint1 = new Point(this.hero.getX(), this.hero.getY());
    int i = paramKeyEvent.getKeyCode();
    Point localPoint2;
    int k;
    int m;
    if (i == 90) {
      localPoint2 = this.mt.getLocation();
      k = localPoint2.x - 200;
      m = localPoint2.y + 50;
      if (k < 0) k = 0;
      this.state.setLocation(k, m);
      this.state.setVisible(!this.usingstate);
      this.usingstate = (!this.usingstate);
      this.mt.requestFocus();
      return;
    }
    if (i == 88) {
      localPoint2 = this.mt.getLocation();
      k = localPoint2.x + 400;
      m = localPoint2.y + 60;
      if (k < 0) k = 0;
      this.book.setLocation(k, m);
      this.book.setVisible(!this.usingbook);
      this.usingbook = (!this.usingbook);
      this.mt.requestFocus();
      return;
    }

    if (isDir(i)) {
      switch (i) { case 39:
        this.hero.setDir(2); localPoint1.x += 1; break;
      case 37:
        this.hero.setDir(1); localPoint1.x -= 1; break;
      case 40:
        this.hero.setDir(0); localPoint1.y += 1; break;
      case 38:
        this.hero.setDir(3); localPoint1.y -= 1;
      }
      if (isOutBound(localPoint1)) {
        updataInfo();
        return;
      }
      int j = this.game.mt[(this.game.curfloor - 1)][localPoint1.x][localPoint1.y];
      if (isWall(j)) {
        updataInfo();
        return;
      }if (isLand(j)) {
        heroMove(localPoint1);
        updataInfo();
        return;
      }if (isItem(j)) {
        this.hero.getItem(j);
        heroMove(localPoint1);
        this.game.playOgg("get");
        updataInfo();
        return;
      }if (isDoor(j)) {
        this.doorxy = new Point(localPoint1.x, localPoint1.y);
        if (this.hero.openDoor(j, this.doorxy)) this.game.playOgg("door");
        updataInfo();
        return;
      }if (isStair(j)) {
        this.hero.setDir(0);
        this.game.curfloor += (j == -10 ? 1 : -1);
        if (this.game.curfloor > this.game.maxfloor)
          this.game.maxfloor = this.game.curfloor;
        if (this.game.curfloor <= 0) {
          this.game.curfloor += 1;
          return;
        }

        if ((this.game.maxfloor >= 6) && (!this.musicflag2)) {
          this.game.stopMusic();
          this.game.playMusic(2);
          this.musicflag2 = true;
        } else if ((this.game.maxfloor >= 11) && (this.game.maxfloor <= 15) && (!this.musicflag3)) {
          this.game.stopMusic();
          this.game.playMusic(3);
          this.musicflag3 = true;
        } else if ((this.game.maxfloor >= 16) && (this.game.maxfloor <= 20) && (!this.musicflag4)) {
          this.game.stopMusic();
          this.game.playMusic(4);
          this.musicflag4 = true;
        } else if ((this.game.maxfloor >= 21) && (this.game.maxfloor <= 25) && (!this.musicflag5)) {
          this.game.stopMusic();
          this.game.playMusic(5);
          this.musicflag5 = true;
        } else if ((this.game.maxfloor >= 26) && (this.game.maxfloor <= 27) && (!this.musicflag6)) {
          this.game.stopMusic();
          this.game.playMusic(6);
          this.musicflag6 = true;
        }

        this.game.playOgg("stair");
        updataInfo();
        return;
      }if (isMonster(j)) {
        heroMove(localPoint1);
        this.game.playOgg("atk");
        this.hero.fight(j);
        updataInfo();
        return;
      }if (j == -32)
        this.game.win();
    } else {
      if (isSaveOrLoad(i)) {
        switch (i) { case 49:
          this.game.save(1); break;
        case 50:
          this.game.save(2); break;
        case 51:
          this.game.save(3); break;
        case 52:
          this.game.save(4); break;
        case 53:
          this.game.load(1); break;
        case 54:
          this.game.load(2); break;
        case 55:
          this.game.load(3); break;
        case 56:
          this.game.load(4);
        }
        updataInfo();
        return;
      }if (isChangeFloor(i)) {
        if (81 == i) {
          if (this.game.curfloor + 2 > this.game.maxfloor) return;
          if (isNearStair(localPoint1)) {
            this.game.curfloor += 1;
            updataInfo();
          }
        } else if (87 == i) {
          if (this.game.curfloor - 1 <= 0) return;
          if (isNearStair(localPoint1)) {
            this.game.curfloor -= 1;
            updataInfo();
          }
        }
      } else if (i == 72) { this.cheath = true;
      } else if (i == 69) { this.cheate = true;
      } else if (i == 82) { this.cheatr = true;
      } else if (i == 79) {
        this.cheato = true;
        if ((this.cheath) && (this.cheate) && (this.cheatr) && (this.cheato)) {
          this.hero.setHp(999999);
          this.hero.setAtk(99999);
          this.hero.setDef(99999);
          this.hero.setYkey(9999);
          this.hero.setBkey(9999);
          this.hero.setRkey(9999);
          updataInfo();
        }
      }
    }
  }
  public void keyTyped(KeyEvent paramKeyEvent) {  }

  public void keyReleased(KeyEvent paramKeyEvent) {  }


  void drawEdge(Graphics paramGraphics) { 
	  for (int i = 0; i < 12; i++) {
	      Image localImage = new ImageIcon("pic/-1.png").getImage();
	      paramGraphics.drawImage(localImage, 0, i * 32, 32, 32, this);
	      paramGraphics.drawImage(localImage, i * 32, 0, 32, 32, this);
	      paramGraphics.drawImage(localImage, 352, i * 32, 32, 32, this);
	      paramGraphics.drawImage(localImage, i * 32, 352, 32, 32, this);
	  } 
  	}

  void drawLand(Graphics paramGraphics) {
    Image localImage = new ImageIcon("pic/bg.png").getImage();
    paramGraphics.drawImage(localImage, 32, 32, this);
  }
  void drawMap(Graphics paramGraphics) {
    for (int i = 0; i < 10; i++)
      for (int j = 0; j < 10; j++) {
        int k = this.game.mt[(this.game.curfloor - 1)][i][j];
        if ((!this.isopeningdoor) || (this.doorxy.x != i) || (this.doorxy.y != j))
          //是否怪，对应mt数组里面的值，并且第2维i代码x坐标（左到右起第几列），第3维j代表了y坐标（上到下第几行）	
          if (isMonster(k)) drawMonster(paramGraphics, new Point(i, j), k); else
            drawStatic(paramGraphics, new Point(i, j), k);
      }
    if (this.isopeningdoor) drawDoor(paramGraphics, this.doorxy, this.game.mt[(this.game.curfloor - 1)][this.doorxy.x][this.doorxy.y]); 
  }

  void drawHero(Graphics paramGraphics) { Image localImage = new ImageIcon("pic/hero.png").getImage();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    if (this.herocnt == 0) {
      i = (this.hero.getX() + 1) * 32;
      j = (this.hero.getY() + 1) * 32;
      k = 0;
      m = this.hero.getDir() * 32;
    } else {
      if (this.hero.getDir() == 0) {
        i = (this.hero.getX() + 1) * 32;
        j = (this.hero.getY() + 1) * 32 + this.herocnt * 8;
      }
      else if (this.hero.getDir() == 3) {
        i = (this.hero.getX() + 1) * 32;
        j = (this.hero.getY() + 1) * 32 - this.herocnt * 8;
      }
      else if (this.hero.getDir() == 1) {
        i = (this.hero.getX() + 1) * 32 - this.herocnt * 8;
        j = (this.hero.getY() + 1) * 32;
      }
      else if (this.hero.getDir() == 2) {
        i = (this.hero.getX() + 1) * 32 + this.herocnt * 8;
        j = (this.hero.getY() + 1) * 32;
      }

      k = this.herocnt % 4 * 32;
      m = this.hero.getDir() * 32;
    }
    paramGraphics.drawImage(localImage, i, j, i + 32, j + 32, k, m, k + 32, m + 32, this);

    if (this.herocnt >= 3) this.hero.moved();  } 
  void drawMonster(Graphics paramGraphics, Point paramPoint, int paramInt)
  {
    Image localImage = new ImageIcon("pic/m" + (paramInt + 3) / 4 + ".png").getImage();
    int i = (paramPoint.x + 1) * 32;
    int j = (paramPoint.y + 1) * 32;
    int k = this.monstercnt * 32;
    int m = (paramInt - 1) % 4 * 32;
    //这个方法主要用于切图
    paramGraphics.drawImage(localImage, i, j, i + 32, j + 32, k, m, k + 32, m + 32, this);
  }
  void drawStatic(Graphics paramGraphics, Point paramPoint, int paramInt) {
    Image localImage = new ImageIcon("pic/" + paramInt + ".png").getImage();
    int i = (paramPoint.x + 1) * 32;
    int j = (paramPoint.y + 1) * 32;
    paramGraphics.drawImage(localImage, i, j, 32, 32, this);
  }
  void drawDoor(Graphics paramGraphics, Point paramPoint, int paramInt) {
    int i = 0;
    if (paramInt == -12) i = 1;
    else if (paramInt == -13) i = 2;
    else if (paramInt == -14) i = 3;

    Image localImage = new ImageIcon("pic/door.png").getImage();

    int j = (paramPoint.x + 1) * 32;
    int k = (paramPoint.y + 1) * 32;
    int m = (i - 1) * 32;
    int n = this.doorcnt * 32;

    paramGraphics.drawImage(localImage, j, k, j + 32, k + 32, m, n, m + 32, n + 32, this);
  }

  boolean isDir(int paramInt) {
    if ((paramInt == 37) || (paramInt == 39) || (paramInt == 40) || (paramInt == 38))
      return true;
    return false;
  }
  boolean isSaveOrLoad(int paramInt) {
    if ((paramInt == 49) || (paramInt == 50) || (paramInt == 51) || (paramInt == 52) || (paramInt == 53) || (paramInt == 54) || (paramInt == 55) || (paramInt == 56))
    {
      return true;
    }return false;
  }
  boolean isChangeFloor(int paramInt) {
    if ((paramInt == 81) || (paramInt == 87)) return true;
    return false;
  }
  boolean isOutBound(Point paramPoint) {
    if ((paramPoint.x < 0) || (paramPoint.y < 0) || (paramPoint.x >= 10) || (paramPoint.y >= 10)) return true;
    return false;
  }
  boolean isWall(int paramInt) {
    if (-1 == paramInt) return true;
    return false;
  }
  boolean isLand(int paramInt) {
    if ((paramInt == 0) || (paramInt == -19)) return true;
    return false;
  }
  boolean isDoor(int paramInt) {
    if ((paramInt == -12) || (paramInt == -13) || (paramInt == -14)) return true;
    return false;
  }
  boolean isItem(int paramInt) {
    boolean bool = false;
    switch (paramInt) { case -2:
      bool = true; break;
    case -3:
      bool = true; break;
    case -4:
      bool = true; break;
    case -5:
      bool = true; break;
    case -6:
      bool = true; break;
    case -9:
      bool = true; break;
    case -8:
      bool = true; break;
    case -7:
      bool = true;
    }
    if ((paramInt <= -20) && (paramInt >= -29))
      bool = true;
    return bool;
  }
  boolean isMonster(int paramInt) {
    if (paramInt > 0) return true;
    return false;
  }
  boolean isStair(int paramInt) {
    if ((-10 == paramInt) || (-11 == paramInt)) return true;
    return false;
  }
  boolean isNearStair(Point paramPoint) {
    if ((-10 == this.game.mt[(this.game.curfloor - 1)][(paramPoint.x - 1)][paramPoint.y]) || (-10 == this.game.mt[(this.game.curfloor - 1)][(paramPoint.x + 1)][paramPoint.y]) || (-11 == this.game.mt[(this.game.curfloor - 1)][(paramPoint.x - 1)][paramPoint.y]) || (-11 == this.game.mt[(this.game.curfloor - 1)][(paramPoint.x + 1)][paramPoint.y]))
    {
      return true;
    }return false;
  }

  void updataInfo() {
    this.sp.repaint();
    this.bp.showInfo();
    repaint();
  }
  void heroMove(Point paramPoint) {
    this.heroxy = new Point(paramPoint);
    this.hero.moving();
    this.herocnt = 1;
    this.game.mt[(this.game.curfloor - 1)][paramPoint.x][paramPoint.y] = 0;
  }
}