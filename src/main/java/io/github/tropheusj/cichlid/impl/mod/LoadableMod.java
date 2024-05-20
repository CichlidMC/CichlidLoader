package io.github.tropheusj.cichlid.impl.mod;

import java.util.jar.JarFile;

import io.github.tropheusj.cichlid.api.mod.Mod;

public record LoadableMod(Mod mod, JarFile file) {
}
