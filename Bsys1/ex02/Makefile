src = $(wildcard *.asm)
bin = $(src:.asm=)
fil = $(src:.asm=.fil)
hex = $(src:.asm=.hex)
dmp = $(src:.asm=.dmp)

.PHONY: all clean copy

all: $(bin) $(fil) $(hex) $(dmp) copy

%.hex: %
	@hexdump -C $< > $@

%.fil: %
	@file $< > $@

%.dmp: %
	@objdump -t $< > $@  2>/dev/null || true

%: %.asm
	@nasm $< -o $@

ex02-03b: ex02-03b.asm
	@nasm -felf64 $< -o $@

ex02-03d: ex02-03d.asm
	@nasm -felf64 $< -o $@

ex02-03e: ex02-03e.asm
	@nasm -felf64 $< -o $@

copy:
	@cp $(fil) $(hex) $(dmp) "../exercise_solutions/"

clean:
	@rm $(bin)
	@rm $(fil)
	@rm $(hex)
	@rm $(dmp)
