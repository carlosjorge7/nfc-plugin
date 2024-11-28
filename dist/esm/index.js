import { registerPlugin } from '@capacitor/core';
const NFCPlugin = registerPlugin('NFCPlugin', {
    web: () => import('./web').then(m => new m.NFCPluginWeb()),
});
export * from './definitions';
export { NFCPlugin };
//# sourceMappingURL=index.js.map