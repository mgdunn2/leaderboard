load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    sha256 = "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169",
    strip_prefix = "rules_jvm_external-4.0",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/4.0.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    name = "maven",
    artifacts = [
        "com.google.code.findbugs:jsr305:1.3.9",
        "com.google.dagger:dagger:2.22",
        "com.google.dagger:dagger-compiler:2.22",
        "com.google.dagger:dagger-producers:2.22",
        "com.google.code.gson:gson:2.8.5",
        "com.google.guava:guava:31.1-jre",
        "javax.inject:javax.inject:1",
        "org.immutables:gson:2.7.5",
        "org.immutables:value:2.7.5",
    ],
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
    fetch_sources = True,   # Fetch source jars. Defaults to False.
)