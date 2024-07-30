// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "NfcPlugin",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "NfcPlugin",
            targets: ["NFCPluginPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "NFCPluginPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/NFCPluginPlugin"),
        .testTarget(
            name: "NFCPluginPluginTests",
            dependencies: ["NFCPluginPlugin"],
            path: "ios/Tests/NFCPluginPluginTests")
    ]
)