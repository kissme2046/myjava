class HeroShake extends Shake
{
  HeroShake(MyPanel paramMyPanel)
  {
    super(paramMyPanel);
    this.cnt = 1;
  }
  public synchronized void run() {
    while (true) {
      this.mp.herocnt = this.cnt;
      try { Thread.sleep(25L); } catch (Exception localException) {
      }this.cnt += 1;
      if (this.cnt >= 5) return;
      this.mp.repaint();
    }
  }
}