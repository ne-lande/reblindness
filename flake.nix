{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs = { self, nixpkgs }:

  let
    pkgs = nixpkgs.legacyPackages.x86_64-linux;

    jdk = pkgs.jdk21;
    libs = with pkgs; [
        jdk
        libpulseaudio
        libGL
        glfw
        openal
        stdenv.cc.cc.lib

        xorg.libX11
        xorg.libXcursor
        xorg.libXrandr
        xorg.libXinerama
        xorg.libXi
        xorg.libXext
        mesa
      ];
  in {
    devShell.x86_64-linux = pkgs.mkShell {
      packages = [ jdk ];
      buildInputs = libs;
      LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath libs;
      shellHook = ''
          export JAVA_HOME=${jdk}
          export PATH=$JAVA_HOME/bin:$PATH
      '';
    };
  };
}
