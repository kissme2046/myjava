import java.awt.Point;

class Hero extends Stuff
{
  private Point xy;
  private int dir;
  private int ykey;
  private int bkey;
  private int rkey;
  MyPanel mp;
  BookPane bp;
  Game game;
  private HeroShake hs;
  private DoorShake ds;

  Hero(int paramInt1, int paramInt2, int paramInt3, Point paramPoint)
  {
    this.hp = paramInt1;
    this.atk = paramInt2;
    this.def = paramInt3;
    this.xy = paramPoint;
  }

  int getMoney() {
    return this.money;
  }
  void setMoney(int paramInt) {
    this.money = paramInt;
  }

  int getExp() {
    return this.exp;
  }
  void setExp(int paramInt) {
    this.exp = paramInt;
  }

  int getYkey() {
    return this.ykey;
  }
  void setYkey(int paramInt) {
    this.ykey = paramInt;
  }

  int getBkey() {
    return this.bkey;
  }
  void setBkey(int paramInt) {
    this.bkey = paramInt;
  }

  int getRkey() {
    return this.rkey;
  }
  void setRkey(int paramInt) {
    this.rkey = paramInt;
  }

  int getDir() {
    return this.dir;
  }
  void setDir(int paramInt) {
    this.dir = paramInt;
  }

  int getHp() {
    return this.hp;
  }
  void setHp(int paramInt) {
    this.hp = paramInt;
  }

  int getAtk() {
    return this.atk;
  }
  void setAtk(int paramInt) {
    this.atk = paramInt;
  }

  int getDef() {
    return this.def;
  }
  void setDef(int paramInt) {
    this.def = paramInt;
  }

  Point getXY() {
    return this.xy;
  }
  int getX() {
    return this.xy.x;
  }
  int getY() {
    return this.xy.y;
  }

  void setXY(Point paramPoint) {
    this.xy = paramPoint;
  }
  void setX(int paramInt) {
    this.xy.x = paramInt;
  }
  void setY(int paramInt) {
    this.xy.y = paramInt;
  }

  void fight(int paramInt)
  {
    this.hp -= this.bp.judgeDamage(this, paramInt);
    this.money += Game.MMONEY[(paramInt - 1)];
    this.exp += Game.MEXP[(paramInt - 1)];
    if (this.hp <= 0) this.game.lost(); 
  }

  void getItem(int paramInt)
  {
    switch (paramInt) { case -7:
      this.hp += 100; break;
    case -8:
      this.hp += 250; break;
    case -9:
      this.hp += 500; break;
    case -5:
      this.atk += 1; break;
    case -6:
      this.def += 1; break;
    case -2:
      this.ykey += 1; break;
    case -3:
      this.bkey += 1; break;
    case -4:
      this.rkey += 1; break;
    case -20:
      this.atk += 5; break;
    case -21:
      this.atk += 10; break;
    case -22:
      this.atk += 15; break;
    case -23:
      this.atk += 20; break;
    case -24:
      this.atk += 25; break;
    case -25:
      this.def += 5; break;
    case -26:
      this.def += 10; break;
    case -27:
      this.def += 15; break;
    case -28:
      this.def += 20; break;
    case -29:
      this.def += 25;
    case -19:
    case -18:
    case -17:
    case -16:
    case -15:
    case -14:
    case -13:
    case -12:
    case -11:
    case -10: }  } 
  boolean openDoor(int paramInt, Point paramPoint) { boolean bool = false;
    if ((paramInt == -12) && (this.ykey > 0)) {
      this.ykey -= 1;
      bool = true;
    }
    else if ((paramInt == -13) && (this.bkey > 0)) {
      this.bkey -= 1;
      bool = true;
    }
    else if ((paramInt == -14) && (this.rkey > 0)) {
      this.rkey -= 1;
      bool = true;
    }
    if (bool) openingDoor(paramInt);
    return bool; }

  void openingDoor(int paramInt)
  {
    this.ds = new DoorShake(this.mp);
    this.ds.start();
    this.mp.isopeningdoor = true;
  }
  void openedDoor(Point paramPoint) {
    this.ds.stop();
    this.mp.isopeningdoor = false;
    this.game.mt[(this.game.curfloor - 1)][paramPoint.x][paramPoint.y] = 0;
    this.mp.doorcnt = 0;
  }
  void moving() {
    this.hs = new HeroShake(this.mp);
    this.hs.start();
    this.mp.ismoving = true;
  }
  void moved() {
    this.hs.stop();
    this.mp.ismoving = false;
    setXY(this.mp.heroxy);
  }
}