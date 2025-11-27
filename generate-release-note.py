import pyperclip

with open("RELEASE_NOTE_TEMPLATE.md", "r", encoding="utf-8") as f:
    template = f.read()

with open("RELEASE_NOTE.md", "r", encoding="utf-8") as f:
    markdown = f.read()

old_version = input("Old version: ")
new_version = input("New version: ")

pyperclip.copy(
    template.format(
        OLD_VERSION=old_version,
        VERSION=new_version,
        MARKDOWN=markdown
    )
)