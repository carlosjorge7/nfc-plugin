import { WebPlugin } from '@capacitor/core';

import type { NFCPluginPlugin } from './definitions';

export class NFCPluginWeb extends WebPlugin implements NFCPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
