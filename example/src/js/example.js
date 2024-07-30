import { NFCPlugin } from 'nfc-plugin';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    NFCPlugin.echo({ value: inputValue })
}
