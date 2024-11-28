import { WebPlugin } from '@capacitor/core';
export class NFCPluginWeb extends WebPlugin {
    async readTag() {
        console.log('Read Tag method is not implemented in the Web version');
        return { message: 'Not implemented' };
    }
    async writeTag(options) {
        console.log('Write Tag method is not implemented in the Web version');
        console.log('Message:', options.url);
    }
}
//# sourceMappingURL=web.js.map