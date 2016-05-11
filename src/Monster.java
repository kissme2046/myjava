class Monster extends Stuff
{
  public String name;

  Monster(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.name = paramString;
    this.hp = paramInt1;
    this.atk = paramInt2;
    this.def = paramInt3;
    this.money = paramInt4;
    this.exp = paramInt5;
  }
}