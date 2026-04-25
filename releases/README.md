# Pre-built APKs

GitHub blocks files larger than 100 MB on push, and the Sketchware Pro
release APK is ~124 MB, so the binaries themselves live exclusively on
[GitHub Releases](https://github.com/Dr-Code-EG/Sketchware-Pro/releases).
This directory holds checksums and pointers so that an offline clone can
still verify a downloaded APK.

| Tag | APK download | SHA-256 |
|---|---|---|
| `v7.0.0-phase1-r1` | [SketchwarePro-v7.0.0-phase1-r1.apk](https://github.com/Dr-Code-EG/Sketchware-Pro/releases/download/v7.0.0-phase1-r1/SketchwarePro-v7.0.0-phase1-r1.apk) | see [`v7.0.0-phase1-r1.sha256`](./v7.0.0-phase1-r1.sha256) |

## Verifying a downloaded APK

```bash
# Once you've downloaded the APK from the Releases page:
sha256sum -c releases/v7.0.0-phase1-r1.sha256
```

## Why not commit the APK?

GitHub's pre-receive hook rejects pushes containing files > 100 MB.
Switching to git-LFS would solve that but adds friction for casual
contributors who clone the repo. Releases are the canonical
distribution channel and are versioned alongside git tags.
