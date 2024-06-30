package io.github.cichlidmc.cichlid_loader.impl.mod;

import java.util.jar.JarFile;

import io.github.cichlidmc.cichlid_loader.api.mod.Mod;

public record LoadableMod(Mod mod, JarFile file) {
}
