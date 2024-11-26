import { PluginListenerHandle, WebPlugin } from '@capacitor/core';
import type { NFCPluginPlugin } from './definitions';

export class NFCPluginWeb extends WebPlugin implements NFCPluginPlugin {
  async readTag(): Promise<{ message: string }> {
    console.log('Read Tag method is not implemented in the Web version');
    return { message: 'Not implemented' };
  }

  async writeTag(options: { url: string }): Promise<void> {
    console.log('Write Tag method is not implemented in the Web version');
    console.log('Message:', options.url);
  }
}
