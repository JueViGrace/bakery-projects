import type { APIContext } from 'astro';
import { actions } from 'astro:actions';

interface Refresher {
  resetRefresh(): Promise<void>;
  shouldRefresh(): Promise<boolean>;
  updateLastRefresh(date: Date): Promise<void>;
}

class RefresherImpl implements Refresher {
  private static REFRESH_TIME: number = 60000;
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
    let lastRefresh = await this.getLastRefresh();
    if (lastRefresh) {
      const difference = Math.abs(lastRefresh.getTime() - new Date().getTime());
      if (difference >= RefresherImpl.REFRESH_TIME) {
        refresh = true;
      }
    }

    return refresh;
  }
}

export { type Refresher, RefresherImpl };
