# DyAdd

This sample is built with Jetpack Compose, the new UI framework in Android.

## Getting Started

To learn how to load apps on the Surface Duo emulator, see the [documentation](https://docs.microsoft.com/dual-screen/android), and follow [the blog](https://devblogs.microsoft.com/surface-duo).

## Features

The sample uses the [Extended Canvas](https://docs.microsoft.com/dual-screen/introduction#extended-canvas) app pattern to show a basic Calculator on one pane, and additional more advanced grid of equations on the other pane. This sample also has a ticker tape, which is a history of entered equations that can be accessed later. Depending on the orientation, this ticker tape will be on the first or second pane.

![Screenshot](screenshots/single_portrait.png)
![Screenshot](screenshots/single_landscape.png)

When the app is a single landscape or a single portrait. The ticker tape is not visable when in a single landscape to give users a more simple experience, while the portrait is a step above it.

![Screenshot](screenshots/dual_portrait.png)
![Screenshot](screenshots/dual_landscape.png)


When the app is in dual mode, the user is able to make use of the advanced equation grid. If the user is in portrait mode, the ticker tape will stay on the first pane, but when the device is tilted it is moved to the second pane with a different history orientation (most recent on top vs most recent on bottom).

## Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a
Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us
the rights to use your contribution. For details, visit https://cla.opensource.microsoft.com.

When you submit a pull request, a CLA bot will automatically determine whether you need to provide
a CLA and decorate the PR appropriately (e.g., status check, comment). Simply follow the instructions
provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/).
For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or
contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

## License

Copyright (c) Microsoft Corporation.

MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
