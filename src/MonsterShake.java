class MonsterShake extends Shake
{
  MonsterShake(MyPanel paramMyPanel)
  {
    super(paramMyPanel);
  }
  public synchronized void run() {
    while (true) {
      this.mp.monstercnt = this.cnt;
      try { Thread.sleep(150L); } catch (Exception localException) {
      }this.cnt += 1;
      this.cnt %= 4;
      this.mp.repaint();
    }
  }
}