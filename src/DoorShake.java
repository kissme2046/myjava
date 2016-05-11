class DoorShake extends Shake
{
  DoorShake(MyPanel paramMyPanel)
  {
    super(paramMyPanel);
  }
  public synchronized void run() {
    while (true) {
      this.mp.doorcnt = this.cnt;
      try { Thread.sleep(50L); } catch (Exception localException) {
      }this.cnt += 1;
      if (this.cnt >= 5) return;
      this.mp.repaint();
    }
  }
}