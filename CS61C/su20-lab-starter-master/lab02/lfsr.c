#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include "lfsr.h"

void lfsr_calculate(uint16_t *reg) {
    /* YOUR CODE HERE */
	short int zero = *reg;
	short int one = 1;
	zero = one & zero;
	zero = zero ^ ((*reg >> 2) & one);
	zero = zero ^ ((*reg >> 3) & one);
	zero = zero ^ ((*reg >> 5) & one);
	*reg = *reg >> 1;
	if (((*reg >> 15) & 1) != zero) {
		*reg = *reg ^ (one << 15);
	}		
}

