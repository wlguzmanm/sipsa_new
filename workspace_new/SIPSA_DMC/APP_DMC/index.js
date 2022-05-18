import React from "react";
import { AppRegistry } from "react-native";
import {
  Text,
  TextInput,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  useColorScheme,
} from "react-native";

import { Colors } from "react-native/Libraries/NewAppScreen";

import RNFetchBlob from "rn-fetch-blob";
import Home from "./src/Home";

const HelloWorld = () => {
  const [path, setPath] = React.useState(() => {

    //return `${RNFetchBlob.fs.dirs.DownloadDir}`;
    return `${RNFetchBlob.fs.dirs.SDCardDir}/Censo_Economico/matrix`;
  });

  const backgroundStyle = {
    backgroundColor: Colors.lighter,
    display: "flex",
    width: "100%",
  };

  const inputStyle = {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <Home path={path} />
    </SafeAreaView>
  );
};

AppRegistry.registerComponent("MyReactNativeApp", () => HelloWorld);
