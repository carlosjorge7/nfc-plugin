# nfc-plugin

nfc java plugin for ionic app

## Install

```bash
npm install nfc-plugin
npx cap sync
```

## API

<docgen-index>

* [`readTag()`](#readtag)
* [`writeTag(...)`](#writetag)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### readTag()

```typescript
readTag() => Promise<{ message: string; }>
```

**Returns:** <code>Promise&lt;{ message: string; }&gt;</code>

--------------------


### writeTag(...)

```typescript
writeTag(options: { message: string; }) => Promise<void>
```

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ message: string; }</code> |

--------------------

</docgen-api>
