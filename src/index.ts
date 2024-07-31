import { registerPlugin } from '@capacitor/core';
import type { NFCPluginPlugin } from './definitions';

const NFCPlugin = registerPlugin<NFCPluginPlugin>('NFCPlugin', {
  web: () => import('./web').then(m => new m.NFCPluginWeb()),
});

export * from './definitions';
export { NFCPlugin };
