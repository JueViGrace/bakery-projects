import type { APIContext } from 'astro';
import { actions } from 'astro:actions';

interface Refresher {
  resetRefresh(): Promise<void>;
  shouldRefresh(): Promise<boolean>;
  updateLastRefresh(date: Date): Promise<void>;
}

class RefresherImpl implements Refresher {
  private static REFRESH_TIME: number = 3300000;
  private ctx: APIContext;

  constructor(ctx: APIContext) {
    this.ctx = ctx;
  }

  async getLastRefresh(): Promise<Date | null> {
    const { data, error } = await this.ctx.callAction(
      actions.session.getLastRefresh,
      null
    );
    if (!data && error) {
      console.error('Get last refresh error:', error.message);
      return null;
    }

    return new Date(data);
  }

  async resetRefresh(): Promise<void> {
    await this.ctx.callAction(actions.session.resetRefresh, null);
  }

  async updateLastRefresh(date: Date): Promise<void> {
    await this.ctx.callAction(actions.session.updateRefresher, {
      date: date.getTime(),
    });
  }

  async shouldRefresh(): Promise<boolean> {
    let refresh: boolean = false;

    try {
      let lastRefresh = await this.getLastRefresh();
      if (lastRefresh) {
        const difference = Math.abs(
          lastRefresh.getTime() - new Date().getTime()
        );
        if (difference >= RefresherImpl.REFRESH_TIME) {
          refresh = true;
        }
      }
    } catch (e) {
      refresh = false;
      console.error('Should refresh error:', e);
    }

    return refresh;
  }
}

export { type Refresher, RefresherImpl };
