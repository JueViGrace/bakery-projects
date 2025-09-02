interface Refresher {
  resetRefresh(): void;
  shouldRefresh(): boolean;
  updateLastRefresh(date: Date): void;
}

class RefresherImpl implements Refresher {
  private static REFRESH_TIME: number = 3300000;
  private lastRefresh: Date | null = null;

  static getInstance(): Refresher {
    return new RefresherImpl();
  }

  resetRefresh(): void {
    this.lastRefresh = null;
  }

  updateLastRefresh(date: Date): void {
    this.lastRefresh = date;
  }

  shouldRefresh(): boolean {
    let refresh: boolean = false;
    if (this.lastRefresh) {
      const difference = Math.abs(
        this.lastRefresh.getTime() - new Date().getTime()
      );
      if (difference >= RefresherImpl.REFRESH_TIME) {
        refresh = true;
      }
    }
    return refresh;
  }
}

const refresher = RefresherImpl.getInstance();

export { type Refresher, refresher };
